(ns ferry-front.views.analysis
  (:require [cljsjs.chartist]
            [reagent.core :as reagent :refer [atom]]))

(defn show-chart
  []
  (let [chart-data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                    :series [[1 1 6 15 25]]}
        options {:width  "700px"
                 :height "380px"}]
    (js/Chartist.Line. ".ct-chart" (clj->js chart-data) (clj->js options))))

(defn chart-component
  []
  (let [some "state goes here"]
    (reagent/create-class
      {:component-did-mount #(show-chart)
       :display-name        "chart-component"
       :reagent-render      (fn []
                              [:div {:class "ct-chart ct-perfect-fourth"}])})))

