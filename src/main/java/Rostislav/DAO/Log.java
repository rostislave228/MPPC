package Rostislav.DAO;

import org.springframework.stereotype.Component;

@Component
public class Log implements Listener {
	@Override
	public void onEvent(String eventType, Object data) {
        String message;
        switch (eventType) {
		    case "PRINTED_PRODUCT_ADDED":
		        message = "Printed Product added: " + data.toString();
		        break;
	        case "BOOK_ADDED":
	            message = "Book added: " + data.toString();
	            break;
	        case "EBOOK_ADDED":
	            message = "EBook added: " + data.toString();
	            break;
            case "NEWSPAPER_ADDED":
                message = "Newspaper added: " + data.toString();
                break;
            case "PRINTED_PRODUCT_UPDATED":
                message = "Printed Product updated with ID: " + data.toString();
                break;
            case "PRINTED_PRODUCT_DELETED":
                message = "Printed Product deleted with ID: " + data;
                break;
            default:
                message = "Unknown event: " + eventType;
        }
        
        System.out.println("GUI Observer: " + message);
	}
}
