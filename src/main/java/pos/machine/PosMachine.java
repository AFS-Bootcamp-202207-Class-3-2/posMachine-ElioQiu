package pos.machine;

import java.util.HashMap;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        HashMap<String, Integer> barcodesMap = calculateItemNumber(barcodes);
        return null;
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
}
