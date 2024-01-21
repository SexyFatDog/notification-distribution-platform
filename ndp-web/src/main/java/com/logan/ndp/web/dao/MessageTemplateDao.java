package com.logan.ndp.web.dao;

import com.logan.ndp.web.domain.MessageTemplateDo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 消息模板 DAO
 * PO：包括编号、博客标题、博客内容、博客标签、博客分类、博客状态、创建时间、修改时间等。（与数据库表中的字段一样。）
 * VO：在客户端浏览器展示的页面数据，博客标题、博客内容、博客标签、博客分类、创建时间、上一篇博客URL、下一篇博客URL。
 * DTO：在服务端数据传输的对象，编号、博客标题、博客内容、博客标签、博客分类、创建时间、上一篇博客编号、下一篇博客编号。
 * DAO：数据库增删改查的方法，例如新增博客、删除博客、查询所有博客、更新博客。
 * BO：基本业务操作，如管理分类、管理标签、修改博客状态等，是我们常说的service层操作
 */
public interface MessageTemplateDao extends JpaRepository<MessageTemplateDo, Long>, JpaSpecificationExecutor<MessageTemplateDo> {
    /**
     * 查询 列表（分页)
     *
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    List<MessageTemplateDo> findAllByIsDeletedEqualsOrderByUpdatedDesc(Integer deleted, Pageable pageable);


    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);
}
