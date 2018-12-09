(ns ferry-front.views.analysis
  (:require #_[cljsjs.chartist]
            [reagent.core :as reagent :refer [atom]]))

(defn line-example
  []
  (let [chart-data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                    :series [[1 1 6 15 25]]}
        options {:width  "700px"
                 :height "380px"}]
    (js/Chartist.Line. ".ct-chart" (clj->js chart-data) (clj->js options))))


(defn line-bar-example []
  (let [chart-data {:labels [1 2 3 4]
                    :series [[100 120 180 200]]}
        chart-data2 {:labels [1 2 3 4]
                     :series [[5 2 8 3]]}
        options {:width  "700px"
                 :height "600px"
                 :seriesBarDistance 10}]
    (js/Chartist.Bar. "#chart1" (clj->js chart-data options))
    (js/Chartist.Line. ".chart2" (clj->js chart-data options))))

(defn chart-component
  []
  (let [some "state goes here"]
    (reagent/create-class
      {:component-did-mount #(line-bar-example)
       :display-name        "chart-component"
       :reagent-render      (fn []
                              #_[:div {:class "ct-chart ct-perfect-fourth"}]
                              [:div {:id "chart1 ct-perfect-fourth"}]
                              [:div {:class "chart2 ct-perfect-fourth"}])})))


;new Chartist.Pie('.ct-chart', {
;  series: [20, 10, 30, 40]
;}, {
;  donut: true,
;  donutWidth: 60,
;  donutSolid: true,
;  startAngle: 270,
;  showLabel: true
;});

;More than 2 charts on the same page
; <div class="ct-chart ct-golden-section" id="chart1"></div>
;<div class="ct-chart ct-golden-section" id="chart2"></div>
;
;<script>
;  // Initialize a Line chart in the container with the ID chart1
;  new Chartist.Line('#chart1', {
;    labels: [1, 2, 3, 4],
;    series: [[100, 120, 180, 200]]
;  });
;
;  // Initialize a Line chart in the container with the ID chart2
;  new Chartist.Bar('#chart2', {
;    labels: [1, 2, 3, 4],
;    series: [[5, 2, 8, 3]]
;  });
;</script>





