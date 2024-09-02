<template>
  <div id="chart">
    <apexchart type="bar" height="350" :options="chartOptions" :series="sentimentDayAccumulatedChartData"></apexchart>
  </div> 
</template>
<script>


export default {
  /* eslint-disable */
  components: {

  },

  data() {
    return {
      sentimentDayAccumulated: [],
      host: process.env.VUE_APP_BASE_URL,

      series: [{
        name: "positive",
        data: [
        ]
      }, {
        name: "negative",
        data: [
        ]
      }, {
        name: "neutral",
        data: [
        ]
      }],
      chartOptions: {
       
        chart: {
          //height: 350,
            stacked: true,
              stackType: '100%',
          dropShadow: {
            enabled: true,
            color: '#000',
            // top: 18,
            // left: 7,
            // blur: 10,
            // opacity: 0.2
          },
          zoom: {
            enabled: false
          },
          toolbar: {
            show: false
          }
        },
        colors: ['#77B6EA', '#545454', '#116464'],
        dataLabels: {
          //enabled: true,
        },
        // stroke: {
        //   curve: 'smooth'
        // },
        title: {
          text: 'Average Sentiment Positive, Negative and Neutral',
          align: 'left'
        },
        grid: {
          borderColor: '#e7e7e7',
          row: {
            colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
            opacity: 0.5
          },
        },
        markers: {
          //size: 1
        },
        xaxis: {
          categories: [],
        
        },
        yaxis: {
          title: {
            text: 'Sentiment'
          },
          min: 0,
          max: 1
        },
        legend: {
          position: 'top',
          horizontalAlign: 'right',
          floating: false,

        }
      },

    }
  },

  mounted: function () {
    this.fetchSentimentDayAll();

  },
  computed: {
    sentimentDayAccumulatedChartData() {
      //clear
      this.series[0].data = [];
      this.series[1].data = [];
      this.series[2].data = [];
      //this.chartOptions.xaxis.categories = [];

  

      for (var x of  this.sentimentDayAccumulated) {
          console.log(x["result"].toFixed(2))

          if(x["_id"]["label"] == "positive"){
            this.series[0].data.push(x["result"].toFixed(2));
          }

          if(x["_id"]["label"] == "negative"){
            this.series[1].data.push(x["result"].toFixed(2));
          }

          if(x["_id"]["label"] == "neutral"){
            this.series[2].data.push(x["result"].toFixed(2));
          }

          //this.chartOptions.xaxis.categories.push(this.sentimentDayAccumulated[i]["date"]);

      }
      console.log(this.series);
      return this.series;
    },
  },

  methods: {
    sortJobsByTimestamp() {
      this.jobs.sort((a, b) => a.articleTimestamp - b.articleTimestamp);
    },
    //getLast7days
    async fetchSentimentDayAll() {
      try {
        const res = await fetch(this.host + "/api/sentiment/all/limit/24");
        this.sentimentDayAccumulated = (await (res.json()));
        console.log(this.sentimentDayAccumulated);
      } catch {
        console.log("Error fetching sentimentDayAccumulated");
        this.error = "Error fetching sentimentDayAccumulated";
      }
    },
  }
}
</script>