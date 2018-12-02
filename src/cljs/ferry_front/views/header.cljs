(ns ferry-front.views.header)

(defn header []
  [:header
   [:div {:class "h-12 bg-blue-dark"}
    [:h1 {:class "p-1 text-white text-2xl italic"} "Smooth sailing"]]
   ])
