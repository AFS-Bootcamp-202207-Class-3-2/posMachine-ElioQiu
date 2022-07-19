package pos.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        HashMap<String, Integer> barcodesMap = calculateItemNumber(barcodes);
        Receipt receipt = calculateReceipt(barcodesMap);
        return renderReceipt(receipt);
    }

    private HashMap<String, Integer> calculateItemNumber(List<String> barcodes) {
        HashMap<String, Integer> barcodesMap = new HashMap<>();
        for (String barcode : barcodes) {
            if (barcodesMap.containsKey(barcode)) {
                barcodesMap.put(barcode, barcodesMap.get(barcode) + 1);
            } else {
                barcodesMap.put(barcode, 1);
            }
        }
        return barcodesMap;
    }

    private Receipt calculateReceipt(HashMap<String, Integer> barcodesMap) {
        List<ItemInfo> itemList = ItemDataLoader.loadAllItemInfos();
        List<ReceiptItem> receiptList = calculateReceiptItems(itemList, barcodesMap);
        int totalPrice = calculateTotalPrice(receiptList);
        return new Receipt(receiptList, totalPrice);
    }

    private List<ReceiptItem> calculateReceiptItems(List<ItemInfo> itemList, HashMap<String, Integer> barcodesMap) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (ItemInfo itemInfo : itemList) {
            ReceiptItem receiptItem = new ReceiptItem(itemInfo.getName(), barcodesMap.get(itemInfo.getBarcode()),
                    itemInfo.getPrice(), itemInfo.getPrice() * barcodesMap.get(itemInfo.getBarcode()));
            receiptItems.add(receiptItem);
        }
        return receiptItems;
    }

    private int calculateTotalPrice(List<ReceiptItem> receiptList) {
        int total = 0;
        for (ReceiptItem receiptItem : receiptList) {
            total += receiptItem.getSubTotal();
        }
        return total;
    }

    private String renderReceipt(Receipt receipt) {
        StringBuffer sb = new StringBuffer();
        sb.append("***<store earning no money>Receipt***\n");
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            sb.append("Name: ").append(receiptItem.getName()).append(", Quantity: ")
                    .append(receiptItem.getQuantity()).append(", Unit price: ")
                    .append(receiptItem.getUnitPrice()).append(" (yuan), Subtotal: ")
                    .append(receiptItem.getSubTotal()).append(" (yuan)\n");
        }
        sb.append("----------------------\n");
        sb.append("Total: ").append(receipt.getTotalPrice()).append(" (yuan)\n");
        sb.append("**********************");
        return sb.toString();
    }
}
