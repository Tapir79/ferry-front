(ns ferry-front.views.analysis
  (:require [cljsjs.chartist]))



; pie chart with a hole in  the middle
;var ctx = document.getElementById("myChart").getContext('2d');
;
;var myChart = new Chart(ctx, {
;    type: 'doughnut',
;    data: {
;        labels: ["Tokyo",	"Mumbai",	"Mexico City",	"Shanghai"],
;        datasets: [{
;            data: [500,	50,	2424,	14040], // Specify the data values array
;
;            borderColor: ['#2196f38c', '#f443368c', '#3f51b570', '#00968896'], // Add custom color border
;            backgroundColor: ['#2196f38c', '#f443368c', '#3f51b570', '#00968896'], // Add custom color background (Points and Fill)
;            borderWidth: 1 // Specify bar border width
;        }]},
;    options: {
;      responsive: true, // Instruct chart js to respond nicely.
;      maintainAspectRatio: false, // Add to prevent default behaviour of full-width/height
;    }
;});
(defn pie-chart []

  )
;var data = {
;    // A labels array that can contain any sort of values
;    labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
;    // Our series array that contains series objects or in this case series data arrays
;    series: [
;      [5, 2, 4, 2, 0]
;    ]
;  };
(def data
  {:labels ("mon" "tue" "wed" "thu" "fri")
   :series (5 2 4 2 0)})

;new Chartist.Line('.ct-chart', data);
(defn basic-plot []
  (let [line (js/Chartist.Line ".ct-chart" data)]

    )
  ;; JS output: namespace.t1 = new MyType;
  )

(defn analysis-main []
  [:div
   [:h2 "Fancy Statistics"]
   ;<div class="ct-chart ct-perfect-fourth"></div>
   [:div {:class "ct-chart ct-perfect-fourth"}
    [basic-plot]]])

