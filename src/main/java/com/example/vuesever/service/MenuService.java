package com.example.vuesever.service;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vuesever.bean.Menu;
import com.example.vuesever.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService extends BaseService<Menu>{

    @Autowired
    private MenuMapper menuMapper;

    public List<Tree<String>> getMenu(){
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().eq("status",1));

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setChildrenKey("children");
        treeNodeConfig.setNameKey("title");
        treeNodeConfig.setParentIdKey("pid");
        treeNodeConfig.setDeep(3);


        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(menus, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId().toString());
                    tree.setParentId(treeNode.getPid().toString());
                    tree.setWeight(treeNode.getWeight().toString());
                    tree.setName(treeNode.getTitle());
                    // 扩展属性 ...
                    tree.putExtra("path", treeNode.getPath());
                    tree.putExtra("icon", treeNode.getIcon());
                });


        return treeNodes;
    }
}
