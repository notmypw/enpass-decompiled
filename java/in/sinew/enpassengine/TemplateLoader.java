package in.sinew.enpassengine;

import android.content.Context;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.github.clans.fab.BuildConfig;
import java.io.InputStream;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class TemplateLoader implements ContentHandler {
    private Card mCurrentCard;
    private InputStream mIs;

    public TemplateLoader(Context context) {
        int i = R.raw.templates;
        this.mIs = context.getResources().openRawResource(new int[]{R.raw.templates}[0]);
    }

    TemplateLoader(InputStream is) {
        this.mIs = is;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    public void processingInstruction(String target, String data) throws SAXException {
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void skippedEntity(String name) throws SAXException {
    }

    public void startDocument() throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals("card")) {
            String templateType = atts.getValue("templatetype");
            this.mCurrentCard = new Card(templateType, templateType.substring(0, templateType.indexOf(".")), Integer.valueOf(atts.getValue("iconid")).intValue(), atts.getValue(BoxFileVersion.FIELD_NAME));
            TemplateFactory.addTemplate(this.mCurrentCard, templateType);
        }
        if (localName.equals("field")) {
            int uid = Integer.valueOf(atts.getValue("uid")).intValue();
            String label = atts.getValue("label");
            StringBuilder value = new StringBuilder(BuildConfig.FLAVOR);
            String strValue = atts.getValue(BoxMetadataUpdateTask.VALUE);
            if (strValue != null) {
                value = new StringBuilder(strValue);
            }
            String type = atts.getValue(BoxRealTimeServer.FIELD_TYPE);
            this.mCurrentCard.addOrUpdateField(uid, null, label, value, Boolean.valueOf(atts.getValue("sensitive")).booleanValue(), type);
        }
        if (localName.equals("category")) {
            TemplateFactory.addCategory(atts.getValue(BoxFileVersion.FIELD_NAME), atts.getValue(BoxRealTimeServer.FIELD_TYPE), Integer.valueOf(atts.getValue("iconid")).intValue());
        }
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    public void parse() {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            xr.setContentHandler(this);
            xr.parse(new InputSource(this.mIs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
