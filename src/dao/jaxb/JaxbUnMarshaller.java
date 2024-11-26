package dao.jaxb;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.ProductList;

public class JaxbUnMarshaller {

    public ProductList unmarshalProducts(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File(filePath);
            return (ProductList) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
