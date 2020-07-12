-- 资源SQL
INSERT INTO `sys_res` (`parent_id`, `res_type`, `res_name`, `res_url`, `res_perms`, `res_icon`, `order_no`)
    VALUES ('1', '1', '${comments}', '${packageDir}/${webPagename}', NULL, 'fa fa-file', '1');

-- 按钮父资源ID
set @parentId = @@identity;

-- 资源对应按钮SQL
INSERT INTO `sys_res` (`parent_id`, `res_name`, `res_url`, `res_perms`, `res_type`, `res_icon`, `order_no`)
    SELECT @parentId, '查看', null, '${permPrefix}:list', '2', null, '1';
INSERT INTO `sys_res` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_no`)
    SELECT @parentId, '新增', null, '${permPrefix}:save', '2', null, '2';
INSERT INTO `sys_res` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_no`)
    SELECT @parentId, '修改', null, '${permPrefix}:update', '2', null, '3';
INSERT INTO `sys_res` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_no`)
    SELECT @parentId, '删除', null, '${permPrefix}:delete', '2', null, '4';
