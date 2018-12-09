(ns ferry-front.views.analysis
  (:require #_[cljsjs.chartist]
    [reagent.core :as reagent :refer [atom]]))


;new Chartist.Pie('.ct-chart', {
;  series: [20, 10, 30, 40]
;}, {
;  donut: true,
;  donutWidth: 60,
;  donutSolid: true,
;  startAngle: 270,
;  showLabel: true
;});

(defn pie-example []
  (let [chart-data {:series [10 20 80 20]}
        options {:width "250"
                 :startAngle "250"
                 }]
    (js/Chartist.Pie. ".ct-chart3" (clj->js chart-data)  (clj->js options))))

(defn bar-example [data options]
  (let [chart-data data
        chart-options options]
    (js/Chartist.Bar. ".ct-chart2" (clj->js chart-data) (clj->js chart-options))))


(defn line-example []
  (let [chart-data {:labels [1 2 3 4]
                    :series [[100 120 180 200]]}
        options {:width  "250"
                 :height "250"}]
    (js/Chartist.Line. ".ct-chart" (clj->js chart-data) (clj->js options))))

(defn line-component
  [bar-data bar-options]
  (let [some "state goes here"]
    (reagent/create-class
      {:component-did-mount #(line-example)
       :display-name        "line-component"
       :reagent-render      (fn []
                              [:div {:class "ct-chart ct-perfect-fourth"}]
                              #_#_[:div {:id "chart1 ct-golden-section"}]
                                  [:div {:id "chart2 ct-golden-section"}])})))

(defn bar-component
  [bar-data bar-options]
  (let [some "state goes here"]
    (reagent/create-class
      {:component-did-mount #(bar-example bar-data bar-options)
       :display-name        "bar-component"
       :reagent-render      (fn []
                              [:div {:class "ct-chart2 ct-perfect-fourth"}]
                              #_[:div {:class "ct-chart ct-perfect-fourth"}]
                              #_#_[:div {:id "chart1 ct-golden-section"}]
                                  [:div {:id "chart2 ct-golden-section"}])})))

(defn pie-component
  [donut-data donut-options]
  (let [some "state goes here"]
    (reagent/create-class
      {:component-did-mount #(pie-example)
       :display-name        "donut-component"
       :reagent-render      (fn []
                              [:div {:class "ct-chart3 ct-perfect-fourth"}]
                              #_[:div {:class "ct-chart ct-perfect-fourth"}]
                              #_#_[:div {:id "chart1 ct-golden-section"}]
                                  [:div {:id "chart2 ct-golden-section"}])})))


(defn chart-component []
  (let [bar-data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                  :series [[1 1 6 15 25]]}
        bar-options {:width             "300"
                     :height            "300"
                     :seriesBarDistance "5"}]
    [:div
     [bar-component bar-data bar-options]
     [line-component]
     [pie-component]]))


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





