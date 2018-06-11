package com.bingo.mapper;

import com.bingo.model.Param;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ParamMapper {

    // 插入 并查询id 赋给传入的对象
    @Insert("insert into param(id,padding_top,padding_bottom,padding_left,padding_right,columns,rows,space_column,space_row,code_width,code_height,pages,start_number,length,create_time)\n" +
            "values(#{id},#{paddingTop},#{paddingBottom},#{paddingLeft},#{paddingRight},#{columns},#{rows},#{spaceColumn},#{spaceRow},#{codeWidth},#{codeHeight},#{pages},#{startNumber},#{length},#{createTime})")
    int insert(Param model);

    // 根据 ID 查询
    @Select("SELECT * FROM param WHERE id=#{id}")
    Param select(int id);

    // 查询全部
    @Select("SELECT * FROM param")
    List<Param> selectAll();

    // 更新 value
    @Update("UPDATE param SET value=#{value} WHERE id=#{id}")
    int updateValue(Param model);

    // 根据 ID 删除
    @Delete("DELETE FROM param WHERE id=#{id}")
    int delete(Integer id);

    // 查询最新的一条
    @Select("SELECT\n" +
            "\tpadding_top as paddingTop,\n" +
            "\tpadding_bottom as paddingBottom,\n" +
            "\tpadding_left as paddingLeft,\n" +
            "\tpadding_right as paddingRight,\n" +
            "\tcolumns,\n" +
            "\trows,\n" +
            "\tspace_column as spaceColumn,\n" +
            "\tspace_row as spaceRow,\n" +
            "\tcode_width as codeWidth,\n" +
            "\tcode_height as codeHeight,\n" +
            "\tpages,\n" +
            "\tstart_number as startNumber,\n" +
            "\tlength,\n" +
            "\tcreate_time as createTime\n" +
            "FROM\n" +
            "\tparam\n" +
            "ORDER BY\n" +
            "\tcreate_time DESC\n" +
            "LIMIT 1")
    Param selectLast();
}


