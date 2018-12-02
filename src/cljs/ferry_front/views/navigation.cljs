(ns ferry-front.views.navigation)

  (defn main-navigation []
    [:nav {:class "p-1 mb-1 sm:ml-2 lg:ml-4"}
     [:ul {:class "list-reset flex text-grey-light font-medium"}
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:class "text-white cursor-pointer"} "Booking"]]
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:class "cursor-pointer hover:text-white"} "Timetables"]]
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:class "cursor-pointer hover:text-white"} "Analysis"]]
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:class "cursor-pointer hover:text-white"} "Something"]]]])
