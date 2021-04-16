package com.xboot.mybatis.util;

import com.xboot.mybatis.annotation.Field;
import com.xboot.mybatis.annotation.Table;
import com.xboot.mybatis.eo.UserEo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EoUtils {

    public static String getEoTableName(Class clszz) {
        Table annotation = (Table) clszz.getAnnotation(Table.class);
        return annotation.name();
    }

    public static Set<String> getFields(Class clszz) {
        Set<String> set = new HashSet();
        java.lang.reflect.Field[] superField = clszz.getSuperclass().getDeclaredFields();
        java.lang.reflect.Field[] fields = clszz.getDeclaredFields();
        for (java.lang.reflect.Field f : superField) {
            Field annotation = f.getAnnotation(Field.class);
            if (annotation == null) {
                continue;
            }
            set.add(annotation.name());
        }
        for (java.lang.reflect.Field f : fields) {
            Field annotation = f.getAnnotation(Field.class);
            if (annotation == null) {
                continue;
            }
            set.add(annotation.name());
        }
        return set;
    }

    public static String getFieldsSqlStr(Class clszz) {
        Set<String> fields = getFields(clszz);
        StringBuffer sql = new StringBuffer("`id`,");
        List list = new ArrayList(fields);
        for (int i = 0; i < list.size(); i++) {
            sql.append("`");
            sql.append(list.get(i));
            sql.append("`");
            if (i != list.size() - 1) {
                sql.append(",");
            }
        }
        return sql.toString();
    }

    public static void main(String[] args) {
        UserEo u = new UserEo();
        System.out.println(getEoTableName(u.getClass()));
        System.out.println(getFields(u.getClass()));
        System.out.println(getFieldsSqlStr(u.getClass()));
    }
}
