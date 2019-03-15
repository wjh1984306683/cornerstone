# 插入角色
INSERT INTO `sys_role`
VALUES ('1', '1', '2019-02-27 15:47:15', 0, NULL, NULL, 'admin', '管理员', 0);
# 插入用户
INSERT INTO `sys_user`
VALUES ('1', '1', '2019-02-27 15:18:01', 0, NULL, NULL, 1, '管理员',
        '$2a$10$VwKECHVLs5NSFdq0rb/mp./P6yLjUEVR16MKRDS5YYB.GVaCUfksK', 0, 'admin');
# 插入用户据角色关联信息
INSERT INTO `sys_user_roles`
VALUES ('1', '1');

