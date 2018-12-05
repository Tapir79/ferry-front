(ns ferry-front.components.loader)

(defn compass-loader []
  [:div {:class "text-grey-light text-center"}
   [:i {:class "far fa-compass fa-spin text-5xl"}]
   [:div {:class "text-1xl pt-2 pl-1 italic font-semibold"} "Loading"]])
