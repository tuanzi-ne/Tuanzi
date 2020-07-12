package com.spring.common.validator;


import com.spring.common.exception.SysException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 *
 * @author  团子
 * @date 2018/3/20 10:31
 * @since V1.0
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验组
     * @throws SysException 校验不通过，则报异常
     */
    public static void validate(Object object, Class<?>... groups)
            throws SysException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new SysException(constraint.getMessage());
        }
    }
}
