package enums;

public enum Actions {
	/*login*/
	ValidLoginDataCheck, // checks if user exist in the database
	// response from server:
		UsernameExist,
		UsernameDoesNotExist,
		AlreadyLoggedIn,
		GetMyCreditCard,
		GetMyAdress,
		
	Logout,

	// update catalog
	GetProducts, // return arrayList<Product> of all Products
	
	/*products*/
	UpdateProduct,
	DeleteProduct,
	// response from server:
		DeletedProduct, // product added to database
		DeletedProductError, // error while adding product to database
	AddProduct,
	// response from server:
		ProductAdded, // product added to database
		ProductAddedError, // error while adding product to database
		
	/*users manager	 */
	GetUsers, 
	updateUser,
	DeleteUser,
	
	/*surveys manager */
	AddSurvey,
		SurveyAdded,
		SurveyNotAdded,
	GetSurveys,
	UpdateSurvey,
	DeleteSurvey,
		SurveyDeleted,
		SurveyNotDeleted,
	
	/*surveys results manager */
	GetSurveyNames,
	GetSurveyData,
	AddSurveyResults,
	UpdateSurveyResults,
	DeleteSurveyResults,
	GetSurveyResults,
	
	/*authorize users*/
	GetNotAuthorizedUsers,
	AuthorizeUser,
	
	/*send image to server */
	SendImgToServer,
	
	/*survey expert*/
	GetSurveyNamesExpert,
	GetNumberOfVoters,
	GetAvgRes,
	GetConclusion,
	updateConclusion,
	addConclusion,
	checkIfConclusionExist, 
	
	 
	 /*order*/
	 buyProductFromCatalog,
	 GetMyOrdersHistory,
	 CancelOrder,
	 CustomOrderData,
	 
	 /*refund*/
	 AddRefund,
	 
	 /* update my data */
	 AddCreditCard,
	 UpdateCreditCard,
	 AddAddress,
	 UpdateAddress,
	 
	 /*cart*/
	 GetMyCart,
	 AddToCart,
	 GetMyCartCountItems,
	 GetMyCartCountItemsCatalog,
	 DeleteFromCart,
	 GetTotalCartPrice,
	 AddCustomOrder,
	 	CustomAdded,
	 	CustomNotAdded,
	 	
	 /*deals*/
	 AddDeal,
	 DeleteDeal,
	 GetDeals,
	 GetProductsDeals,
	 
	 /*Complains*/
	 AddComplain,
	 GetComplain,
	 DeleteComplain,
	 UpdateComplain,
	 Recompense,
	 GetComplainUsers,
	 
	 
	 /*catalog*/
	 GetProductCatalog,
	 GetDealsCatalog,
	
	/* reports */
	 	GetCartOrders,
		GetRevenue,
		getComplainsReport,
		GetSatisfactionReport,
	
		// 2 screens
		GetSatisfactionReportShop1,
		GetSatisfactionReportShop2,
		GetCartOrdersShop1,
		GetRevenueShop1,
		getComplainsReportShop1,
		GetCartOrdersShop2,
		GetRevenueShop2,
		getComplainsReportShop2;
}
