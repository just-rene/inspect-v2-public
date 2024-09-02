<template>
    <div class='text-h2'>INSPECT </div>
  <div>   
    <v-card >
      <v-tabs
        v-model="tab"
        bg-color="secondary"
        fixed-tabs 
      >
        <v-tab value="one">Multilabel Emotion </v-tab>   
        <v-tab value="two">Sentiment </v-tab>
        <v-tab value="three">NER </v-tab>
        <v-tab value="four">Followed Topics </v-tab>
      </v-tabs>

      <v-card-text>
        <v-tabs-window v-model="tab">
          <v-tabs-window-item value="one">
           <OverviewTable />
          </v-tabs-window-item>

          <v-tabs-window-item value="two">
            <SentimentComponent/>
          </v-tabs-window-item>

          <v-tabs-window-item value="three">
            <NERComponent/>
          </v-tabs-window-item>

          <v-tabs-window-item value="four">
            <FollowedTopics/>
          </v-tabs-window-item>
        </v-tabs-window>
      </v-card-text>
    </v-card>
    

  </div>



</template>

<script>
import NERComponent from './NERComponent.vue';
import OverviewTable from  './OverviewTable.vue';
import SentimentComponent from './SentimentComponent.vue'
import FollowedTopics from './FollowedTopics.vue';



export default {

components: {
  OverviewTable,
  SentimentComponent,
  NERComponent,
  FollowedTopics,
},

  data() {
    return {
      tab: null,
      count: 0,
    }
  }
}
</script>