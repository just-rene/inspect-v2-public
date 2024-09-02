<template>
    <v-row>
        <v-col  v-for="(ner, index) in nerDataSorted" :key="index" >
            <v-card class="mx-auto" prepend-icon="$vuetify" variant="outlined" :subtitle=currentDate >
                <template v-slot:title>
                    <span v-if="index === 'loc'" class="font-weight-black"> Locations </span>
                    <span v-if="index === 'misc'" class="font-weight-black"> Miscellaneous </span>
                    <span v-if="index === 'per'" class="font-weight-black"> Persons </span>
                    <span v-if="index === 'org'" class="font-weight-black"> Organisations </span>
                </template>
                <v-card-text class="bg-surface-light pt-4">                
                    <div v-for="(v,i) in ner" :key="i">
                       {{ v["_id"]["word"] }}   ({{ v["count"] }})  
                    </div>
                </v-card-text>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
export default {

    data() {
        return {
            dates: { timestampStart: 0, timestampEnd: 0 },
            host: process.env.VUE_APP_BASE_URL,
            nerTypes: ["loc", "misc", "per", "org"],
            currentDate: new Date().toISOString().split('T')[0],//"2024-07-28",
            nerData: {
                "loc": [], //array of objects
                "misc": [], //array of objects
                "per": [], //array of objects
                "org": [], //array of objects
            }
        }
    },
    mounted: function () {
        this.setThisWeek();
        this.getAllnersByDay();
    },

    computed: {
        nerDataSorted() {

            if (this.nersByDay !== undefined) {

                var res = this.nerData;
                console.log("type: " + typeof (res))
                console.log(res);

            }
            let sortedObjects = {};
            for(let i = 0; i < this.nerTypes.length; i++ ){
                sortedObjects[[this.nerTypes[i]]] = Object.fromEntries(Object.entries(this.nerData[this.nerTypes[i]])
                .sort((a, b) => {
                    return b[1] - a[1];
                }));
            }
            return sortedObjects;
        },
    },
    methods: {
        getMonday(d) {
            let date = new Date(d)
            const day = date.getDay()
            const diff = date.getDate() - day + (day === 0 ? -6 : 1)
            return new Date(d.setDate(diff))
        },

        setThisWeek() {
            const monday = this.getMonday(new Date());
            const sunday = new Date(monday);
            sunday.setDate(sunday.getDate() + 6);
            this.dates.timestampStart = monday.toISOString().replace('Z', "");
            this.dates.timestampEnd = sunday.toISOString().replace('Z', "");
        },

        async getAllnersByDay() {
            try {
                //today
                for (let nerType of this.nerTypes) {
                    let res = await fetch(this.host + "/api/ner-accumulated/" + nerType + "/day/" + this.currentDate);
                    this.nerData[nerType] = (await (res.json()));
                }
            } catch {
                console.log("Error fetching ner");
                this.error = "Error fetching ner";
            }
        }
    }
}
</script>