package com.hk.kb_graph.neo4j.node;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphResponse {
    private List<GraphNode> nodes;
    private List<GraphLink> links;
    //由于返回前端的数据 需要绘图 每个属性也需要以node形式绘制 这样的话 每个属性也需要以node形式包装
    //并且赋予其一个id,为了保证id的唯一性,所有属性的id从-1开始递减,此处记录当前已使用的id
    private Long propertyIndex;
    private Long deepth;

    public GraphResponse(List<GraphNode> nodes, List<GraphLink> links, Long propertyIndex) {
        this.nodes = nodes;
        this.links = links;
        this.propertyIndex = propertyIndex;
    }
}
