<#assign str='#'/>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package!}.dao.${className!}Dao">
    <!--查询-->
	<select id="queryList" parameterType="map" resultType="${className!}Info">
		select * from ${tableName!}
        <where>
            <if test="id != null">
                and `id` = ${str}{id}
            </if>
        </where>
	</select>

    <!--保存新增-->
	<insert id="save" parameterType="${className!}Info"<#if (pk.extra)?? && (pk.extra) == 'auto_increment'> useGeneratedKeys="true" keyProperty="${(pk.attrname)!}"</#if>>
		insert into ${tableName!}
		(
        <#if columns??>
            <#list columns as column>
             <#if ((column.columnName)?? && (pk.columnName)?? && (column.columnName) != (pk.columnName)) || ((pk.extra)?? && (pk.extra) != 'auto_increment') >
			    `${column.columnName}`<#if (column_index) != (columns?size-1)>,</#if>
             </#if>
	        </#list>
        </#if>
		)
		values
		(
       <#if columns??>
            <#list columns as column>
                <#if ((column.columnName)?? && (pk.columnName)?? && (column.columnName) != (pk.columnName)) || ((pk.extra)?? && (pk.extra) != 'auto_increment') >
                ${str}{${(column.attrname)!}}<#if (column_index) != (columns?size-1)>,</#if>
                </#if>
            </#list>
       </#if>
		)
	</insert>

    <!--更新-->
	<update id="update" parameterType="${className!}Info">
		update ${tableName!}
		<set>
        <#if columns??>
            <#list columns as column>
                <#if ((column.columnName)?? && (pk.columnName)?? && (column.columnName) != (pk.columnName)) || ((pk.extra)?? && (pk.extra) != 'auto_increment') >
                <if test="${(column.attrname)!} != null">`${(column.columnName)!}`=${str}{${(column.attrname)!}}<#if (column_index) != (columns?size-1)>,</#if></if>
                </#if>
            </#list>
        </#if>
		</set>
		where ${(pk.columnName)!} = ${str}{${(pk.attrname)!}}
	</update>

    <!--批量删除-->
	<delete id="delete">
		delete from ${tableName!} where ${(pk.columnName)!} in
		<foreach item="${(pk.attrname)!}" collection="array" open="(" separator="," close=")">
			${str}{${(pk.attrname)!}}
		</foreach>
	</delete>
</mapper>