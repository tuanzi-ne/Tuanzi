package ${package!}.pojo;

import java.io.Serializable;
import java.util.*;

/**
 * ${comments!}
 *
 * @author ${author!}
 * @date ${datetime!}
 * @since V1.0
 */
public class ${className!}Info implements Serializable {
	private static final long serialVersionUID = 1L;

<#list columns as column>
    /**
     * ${(column.columnComment)!}
     */
	private ${(column.attrType)!} ${(column.attrname)!};
</#list>

<#list columns as column>
    /**
     * ${(column.columnComment)!}
     */
    public void set${(column.attrName)!}(${(column.attrType)!} ${(column.attrname)!}) {
        this.${(column.attrname)!} = ${(column.attrname)!};
    }
    public ${(column.attrType)!} get${(column.attrName)!}() {
        return ${(column.attrname)!};
    }
</#list>
}
