package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Constants
import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.networkBoundResource
import com.enterprise.agino.data.mapper.toGraphData
import com.enterprise.agino.data.remote.api.GraphService
import com.enterprise.agino.domain.model.GraphData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GraphRepository @Inject constructor(
    private val graphService: GraphService
) {
    fun fetchGraphData(): Flow<Resource<GraphData>> {
        return networkBoundResource(
            fetch = {
                graphService.fetchGraphData(Constants.GRAPH_API).body()!!
            },
            mapFetchedValue = {
                it.toGraphData()
            }
        )
    }
}