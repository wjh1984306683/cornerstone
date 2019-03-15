package com.cs.base.common.cache.core.message;

import com.cs.base.common.cache.core.util.SerializableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

/**
 * 命令消息封装 格式： 第1-4个字节为命令代码，长度4 [SRC_ID] 第5个字节为命令代码，长度1 [TYPE] 第6个字节为命令代码，长度1
 * [OPT] 第7-8个字节为region长度，长度2 [R_LEN] 第9、N 为 region 值，长度为 [R_LEN] 第N+1、N+2 为 key
 * 长度，长度2 [K_LEN] 第N+3、M为 key值，长度为 [K_LEN]
 *
 * @author liyuan
 * @date 2019/2/20 15:23
 */
public class Command {

    private static Logger log = LoggerFactory.getLogger(Command.class);
    /**
     * 命令源标识，随机生成
     */

    private final static int SRC_ID = genRandomSrc();

    /**
     * 删除缓存
     */
    public final static byte OPT_DELETE_KEY = 0x01;
    /**
     * 清除缓存
     */
    public final static byte OPT_CLEAR_KEY = 0x02;

    /**
     * 删除缓存
     */
    public final static byte TYPE_ENTITY_KEY = 0x01;
    /**
     * 清除缓存
     */
    public final static byte TYPE_LIST_KEY = 0x02;
    /**
     * 删除缓存
     */
    public final static byte TYPE_SET_KEY = 0x03;
    /**
     * 清除缓存
     */
    public final static byte TYPE_MAP_KEY = 0x04;

    public final static String REGION_L1_KEY = "l1";
    public final static String REGION_L2_KEY = "l2";
    public final static String REGION_ALL_KEY = "all";

    private int src;
    /**
     * 操作指令
     */
    private byte operator;
    /**
     * 范围
     */
    private String region;
    /**
     * key
     */
    private Object key;
    /**
     * 存储类型
     */
    private byte type;

    private static int genRandomSrc() {
        long ct = System.currentTimeMillis();
        Random rnd_seed = new Random(ct);
        return (int) (rnd_seed.nextInt(10000) * 1000 + ct % 1000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Command cmd = new Command(OPT_DELETE_KEY, "users", "ld" + i, (byte) 1);
            byte[] bufs = cmd.toBuffers();
            System.out.print(cmd.getSrc() + ":");
            for (byte b : bufs) {
                System.out.printf("[%s]", Integer.toHexString(b));
            }
            System.out.println();
            Command cmd2 = Command.parse(bufs);
            System.out.printf("%d -> %d:%s:%s(%s)\n", cmd2.getSrc(), cmd2.getOperator(), cmd2.getRegion(),
                    cmd2.getKey(), cmd2.isLocalCommand());
        }
    }

    public Command() {

    }

    public Command(byte o, String r, Object k, byte type) {
        this.operator = o;
        this.region = r;
        this.key = k;
        this.src = SRC_ID;
        this.type = type;
    }

    public byte[] toBuffers() {
        byte[] keyBuffers = null;
        try {
            keyBuffers = SerializableUtil.serialize(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int r_len = region.getBytes().length;
        int k_len = keyBuffers.length;

        byte[] buffers = new byte[10 + r_len + k_len];
        System.arraycopy(int2bytes(this.src), 0, buffers, 0, 4);
        int idx = 5;
        buffers[idx - 1] = type;
        buffers[idx] = operator;
        buffers[++idx] = (byte) (r_len >> 8);
        buffers[++idx] = (byte) (r_len & 0xFF);
        System.arraycopy(region.getBytes(), 0, buffers, ++idx, r_len);
        idx += r_len;
        buffers[idx++] = (byte) (k_len >> 8);
        buffers[idx++] = (byte) (k_len & 0xFF);
        System.arraycopy(keyBuffers, 0, buffers, idx, k_len);
        return buffers;
    }

    public static Command parse(byte[] buffers) {
        Command cmd = null;
        try {
            int idx = 5;
            byte type = buffers[idx - 1];
            byte opt = buffers[idx];
            int r_len = buffers[++idx] << 8;
            r_len += buffers[++idx];
            if (r_len > 0) {
                String region = new String(buffers, ++idx, r_len);
                idx += r_len;
                int k_len = buffers[idx++] << 8;
                k_len += buffers[idx++];
                if (k_len > 0) {
                    // String key = new String(buffers, idx, k_len);
                    byte[] keyBuffers = new byte[k_len];
                    System.arraycopy(buffers, idx, keyBuffers, 0, k_len);
                    Object key = SerializableUtil.deserialize(keyBuffers);
                    cmd = new Command(opt, region, key, type);
                    cmd.src = bytes2int(buffers);
                }
            }
        } catch (Exception e) {
            log.error("Unabled to parse received command.", e);
        }
        return cmd;
    }

    private static byte[] int2bytes(int i) {
        byte[] b = new byte[4];

        b[0] = (byte) (0xff & i);
        b[1] = (byte) ((0xff00 & i) >> 8);
        b[2] = (byte) ((0xff0000 & i) >> 16);
        b[3] = (byte) ((0xff000000 & i) >> 24);

        return b;
    }

    private static int bytes2int(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    public boolean isLocalCommand() {
        return this.src == SRC_ID;
    }

    public byte getOperator() {
        return operator;
    }

    public void setOperator(byte operator) {
        this.operator = operator;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public int getSrc() {
        return src;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

}
