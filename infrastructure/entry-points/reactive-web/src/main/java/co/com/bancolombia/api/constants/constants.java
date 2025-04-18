package co.com.bancolombia.api.constants;

public final class constants {

    //PATH CONSTANTS
    public static final String FRANCHISE_ROUTER_FUNCTION = "franchiseRouterFunction";
    public static final String PATH_PRODUCT = "/api/v1/product";
    public static final String PATH_BRANCH_PRODUCT = "/api/v1/branch-product";
    public static final String PATH_BRANCH = "/api/v1/branch";
    public static final String PATH_FRANCHISE = "/api/v1/franchise";
    public static final String PATH_DELETE_PRODUCT_FROM_BRANCH = "/api/v1/branch/{branchId}/product/{productId}";
    public static final String PATH_UPDATE_STOCK = "/api/v1/branch/{branchId}/product/{productId}/stock";
    public static final String PATH_UPDATE_FRANCHISE_NAME = "/api/v1/franchise/{franchiseId}/rename";
    public static final String PATH_UPDATE_BRANCH_NAME = "/api/v1/branch/{branchId}/rename";
    public static final String PATH_UPDATE_PRODUCT_NAME = "/api/v1/product/{productId}/rename";
    public static final String PATH_TOP_PRODUCTS_BY_BRANCH = "/api/v1/franchise/{franchiseId}/top-products-by-branch";

    // PATH VARIABLES
    public static final String PATH_VARIABLE_FRANCHISE_ID = "franchiseId";
    public static final String PATH_VARIABLE_BRANCH_ID = "branchId";
    public static final String PATH_VARIABLE_PRODUCT_ID = "productId";

    // QUERY PARAMS
    public static final String NAME = "name";
    public static final String EMPTY = "";

    // Error Messages
    public static final String ERROR_MISSING_NAME_QUERY_PARAM = "Missing required query param: name";

    private void Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
