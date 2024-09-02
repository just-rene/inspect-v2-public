<template>

  <h1 class=error>{{ error }}</h1>

  <v-chip class="ma-2" @click="toggleEmotionFilter('anticipation')" :variant="emotions.anticipation" label>anticipation
  </v-chip>

  <v-chip class="ma-2" @click="toggleEmotionFilter('joy')" :variant="emotions.joy" label>joy</v-chip>
  <v-chip class="ma-1" @click="toggleEmotionFilter('anger')" :variant="emotions.anger" label>anger</v-chip>
  <v-chip class="ma-1" @click="toggleEmotionFilter('fear')" :variant="emotions.fear" label>fear</v-chip>
  <v-chip class="ma-1" @click="toggleEmotionFilter('disgust')" :variant="emotions.disgust" label>disgust</v-chip>
  <v-chip class="ma-1" @click="toggleEmotionFilter('optimism')" :variant="emotions.optimism" label>optimism</v-chip>

  <v-table class="red">
    <thead>
      <th class="red">logo</th>
      <th>headline</th>
      <th>emotions</th>
      <th>link</th>
      <th>date</th>
    </thead>
    <tbody>
      <tr @click="showDetails(job)" v-for="job in filteredJobs " v-bind:key="job.url">
        <td v-if="job.name == 'Watcher Guru BRICS'"><img style="width:20px" src="/watcher.gurou.favicon.ico"> </td>
        <td v-else><img style="width:20px" class="imageSmall" src="/aljazeera.favicon.ico"></td>
        <td class="text-left" v-if="job.scrap != null"> {{ job.scrap.headline }}</td>
        <td v-else> - </td>
        <td v-if="job.scrap != null">{{ job.emotionMultilabel["result"][0]["label"] }} <br> {{
          job.emotionMultilabel["result"][0]["score"] }}</td>
        <td v-else> - </td>
        <td><a :href="job.url">[link]</a></td>
        <td>{{ job.localDateTime }}</td>
      </tr>
    </tbody>
  </v-table>
</template>
<script>


export default {

  name: 'HelloWorld',
  props: {
    msg: String,
  },

  data() {
    return {
      tab: null,
      emotions: {
        anticipation: "elevated",
        joy: "elevated",
        anger: "elevated",
        fear: "elevated",
        optimism: "elevated",
        disgust: "elevated",
      },
      count: 0,
      jobs: [],
      host: process.env.VUE_APP_BASE_URL,
      error: null,
      portalFilter: "off",
      emotionFilter: "on",
      dates: { timestampStart: 0, timestampEnd: 0 },
    }
  },
  computed: {
    filteredJobs() {
      if (this.portalFilter == "on") {
        let result = [];
        for (let i = 0; i < this.jobs.length; i++) {
          if (this.jobs[i].name == this.portalFilter) {
            result.push(this.jobs[i]);
          }
        }
        return result;
      }

      if (this.emotionFilter == "on") {
        let result = [];
        for (let i = 0; i < this.jobs.length; i++) {

          if (this.jobs[i].emotionMultilabel == null || this.jobs[i].emotionMultilabel == null) {
            continue;
          }

          let label = this.jobs[i].emotionMultilabel["result"][0].label;

          if (this.emotions[label] == "elevated") {
            result.push(this.jobs[i]);
          }
        }
        return result;
      }
      return this.jobs;
    },
  },
  mounted: function () {
    this.setThisWeek();
    this.getAllJobs();
    this.sortJobsByTimestamp();
  },
  methods: {
    //todo use mixin instead
    getMonday(d) {
      let date = new Date(d)
      const day = date.getDay()
      const diff = date.getDate() - day + (day === 0 ? -6 : 1)
      return new Date(d.setDate(diff))
    },
    //todo use mixin instead
    setThisWeek() {
      const monday = this.getMonday(new Date());
      const sunday = new Date(monday);
      sunday.setDate(sunday.getDate() + 6);
      this.dates.timestampStart = monday.toISOString().replace('Z', "");
      this.dates.timestampEnd = sunday.toISOString().replace('Z', "");
    },

    toggleEmotionFilter(e) {
      this.emotions[e] == "plain" ? this.emotions[e] = "elevated" : this.emotions[e] = "plain";
    },
    showDetails(job) {
      console.log(job);
    },
    sortJobsByTimestamp() {
      this.jobs.sort((a, b) => a.localDateTime - b.localDateTime);
    },

    async getAllJobs() {
      try {
        const res = await fetch(this.host + "/api/jobs/from/" + this.dates.timestampStart + "/to/" + this.dates.timestampEnd);
        this.jobs = (await (res.json()));
        this.sortJobsByTimestamp();
      } catch {
        console.log("Error fetching jobs");
        this.error = "Error fetching jobs";
      }
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.image-small {
  display: inline-block;
  width: 10px;

}


.error {
  color: red;
}

.success {
  color: green;
}

.text-left {
  text-align: left;
}


h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: flexbox;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
