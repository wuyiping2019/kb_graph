package com.hk.kb_graph.neo4j.service;


import com.hk.kb_graph.neo4j.node.GraphLink;
import com.hk.kb_graph.neo4j.node.GraphNode;
import com.hk.kb_graph.neo4j.node.GraphResponse;
import com.hk.kb_graph.neo4j.node.NodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


@Service
@Slf4j
public class NodeEntityService {
    @Autowired
    private Driver driver;

    public List<NodeEntity> getHomeNodes() {
        List<NodeEntity> nodes = new ArrayList<>();
        String cypher = "match (p) where length(p.name) >= 6 and length(p.name) <= 10 return id(p) as id,p.name as name,labels(p) as type limit 40";
        Session session = driver.session();
        List<Record> records = session.readTransaction(tx -> tx.run(cypher).list());
        ListIterator<Record> recordListIterator = records.listIterator();
        while (recordListIterator.hasNext()) {
            Record record = recordListIterator.next();
            Map<String, Object> map = record.asMap();
            nodes.add(new NodeEntity(map));
        }
        return nodes;
    }

    /**
     * 通过实体ID查询节点的属性
     */
//    public List<GraphNode> getNodeProperties(Long id) {
//        List<GraphNode> nodes = new ArrayList<>();
//        if (id == null) {
//            return nodes;
//        }
//        String cypher = "match (p) where id(p) = " + id + " return p";
//        Session session = driver.session();
//        List<Record> records = session.readTransaction(tx -> tx.run(cypher).list());
//        System.out.println(records);
//        log.info(cypher);
//        log.info("Searched records:" + records.size());
//        log.info(records.toString());
//        if (records.size() == 0) {
//            return nodes;
//        }
//        Record record = records.get(0);
//        Map<String, Object> map = record.values().get(0).asMap();
//        map.forEach(new BiConsumer<String, Object>() {
//            @Override
//            public void accept(String key, Object value) {
//                nodes.add(new GraphNode(key, (String) value, "property"));
//            }
//        });
//        return null;
//    }

    /**
     * 通过节点name获取节点id done
     */
    public List<Long> getNodeIdByName(String name) {
        List<Long> ids = new ArrayList<>();
        if (name == null) {
            return ids;
        }
        String cypher = "match (p) where p.name = '" + name + "' return id(p) as id";
        List<Record> records = driver.session().readTransaction(tx -> tx.run(cypher).list());
        records.forEach(record -> {
            Long id = record.values().get(0).asLong();
            ids.add(id);
        });
        return ids;
    }

    /**
     * 通过节点id查询该节点的属性 done
     */
    public GraphResponse getNodeProperties(GraphResponse graphResponse, Long id) {
        List<GraphNode> nodes = graphResponse.getNodes();
        List<GraphLink> links = graphResponse.getLinks();
        //查询指定id节点的属性 获取节点的标签和属性
        String cypher = "match (p) where id(p) = " + id + " return labels(p) as labels,properties(p) as properties";
        List<Record> records = driver.session().readTransaction(tx -> tx.run(cypher).list());
        Record record;
        if (records.size() == 0) {
            //当没有查到时,直接返回
            return graphResponse;
        } else {
            //当查到时 根据id也只会查到一个Record
            record = records.get(0);
        }
        //将Record转为map集合
        Map<String, Object> map = record.asMap();
        //labels应该为一个列表 只取一个标签
        List<String> labels = (List<String>) map.get("labels");
        String label = labels.get(0);
        //将properties转为map
        Map<String, String> properties = (Map<String, String>) map.get("properties");

        //所有的属性都以node节点形式返回前端
        for (String property : properties.keySet()) {
            //当property为name时
            if (property.equals("name")) {
                nodes.add(new GraphNode(id.toString(), properties.get("name"), label));
            } else {
                Long index = graphResponse.getPropertyIndex() - 1L;
                nodes.add(new GraphNode(index.toString(), properties.get(property), label + "-property"));
                //处理node之间的关系
                links.add(new GraphLink(index.toString(), id.toString(), property));
                graphResponse.setPropertyIndex(index);
            }
        }
        return graphResponse;
    }

    /**
     * 通过节点id查询与该节点关联的id并写入节点之间的关系 查询出r和q
     */
    public List<Long> getRelationNodeIds(GraphResponse graphResponse, Long id) {
        List<GraphNode> nodes = graphResponse.getNodes();
        List<GraphLink> links = graphResponse.getLinks();
        List<Long> ids = new ArrayList<>();
        String cypher = "match (p)-[r]->(q) where id(p) = " + id + " return type(r) as relation,id(q) as id,q.name as name,labels(q) as labels";
        System.out.println(cypher);
        List<Record> records = driver.session().readTransaction(tx -> tx.run(cypher).list());
//        Record<{relation: "保险产品", id: 28442, name: "弘康附加佑安综合医疗保险"}>
        int size = records.size();
        if(size > 10){
            //需要进行截断 需要额外增加一个节点
            Long index = graphResponse.getPropertyIndex();
            index -= 1;
            nodes.add(new GraphNode(index.toString(),"信息太多暂不展示","其它"));
            links.add(new GraphLink(index.toString(),id.toString(),"其它"));
            graphResponse.setPropertyIndex(index);
        }
        int i = 1;
        for (Record record : records) {
            if(i > 10){
                break;
            }
            Map<String, Object> map = record.asMap();
            //将关系强转为String
            String cur_relation = (String) map.get("relation");
            //将id强转为Long
            Long cur_id = (Long) map.get("id");
            //保存进ids
            ids.add(cur_id);
            //将name强转为Long
            String cur_name = (String) map.get("name");
            //节点label转为list并且只取第一个label
            List<String> labels = (List<String>) map.get("labels");
            String label = labels.get(0);
            nodes.add(new GraphNode(cur_id.toString(), cur_name, label));
            //保存关系
            links.add(new GraphLink(cur_id.toString(), id.toString(), cur_relation));

            i += 1;
        }
        return ids;
    }

    /**
     * 获取一个node的两层关系数据
     */

    public GraphResponse getGraphWithDepth(GraphResponse graphResponse, Long id) {
        GraphResponse response = getNodeProperties(graphResponse, id);
        return null;

    }
}
