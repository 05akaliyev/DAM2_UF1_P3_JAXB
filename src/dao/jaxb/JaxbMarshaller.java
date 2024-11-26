package dao.jaxb;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import model.ProductList;

public class JaxbMarshaller {

    public boolean marshalProducts(ProductList productList) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Generate file name with current date
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String fileName = "jaxb/inventory_" + date + ".xml";
            File file = new File(fileName);

            marshaller.marshal(productList, file);
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }
}
