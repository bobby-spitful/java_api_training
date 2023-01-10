package fr.lernejo.navy_battle.transactions;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JSONGameStart implements JSONNavyObject {

    public final String DEFAULT_PATTERN = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";

    private final String id;
    private final String url;
    private final String message;

    public JSONGameStart( URL url, String message ) {
        this.id = getIdFromPattern( DEFAULT_PATTERN );
        this.url = url.toString();
        this.message = message;
    }

    public JSONGameStart(String toParse) {
        JSONObject parsedJSON = new JSONObject(toParse);
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("GameStart.json")) {
            JSONObject jsonSchema = new JSONObject( new JSONTokener(is));
            SchemaLoader.load(jsonSchema).validate(parsedJSON);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.id = (String) parsedJSON.get("id");
            this.url = (String) parsedJSON.get("url");
            this.message = (String) parsedJSON.get("message");
        }
    }

    public String getIdFromPattern( String pattern ) {
        char[] chars = pattern.toCharArray();
        StringBuilder generatedId = new StringBuilder();
        for ( char aChar : chars ) {
            char nextChar = aChar;
            if ( aChar == 'x' ) {
                nextChar = getRandomChar();
            }
            generatedId.append( nextChar );
        }
        return generatedId.toString();
    }

    private char getRandomChar() {
        Random r = new Random();
        int randomValue = r.nextInt( 16 );
        if ( randomValue < 10 ) {
            return (char)( randomValue + '0' );
        } else {
            return (char)( ( randomValue % 10 ) + 'a' );
        }
    }

    @Override
    public String getJSONString() { return this.getJSON().toString(); }

    @Override
    public JSONObject getJSON() {
        JSONObject returnJSON = null;
        try ( InputStream is = this.getClass().getClassLoader().getResourceAsStream( "GameStart.json" ) ) {
            JSONObject jsonSchema = new JSONObject( new JSONTokener( is ) );
            Map<String, Object> returnObject = new HashMap<>();
            returnObject.put( "id", this.id );
            returnObject.put( "url", String.valueOf( this.url ) );
            returnObject.put( "message", this.message );
            returnJSON = new JSONObject( returnObject );
            SchemaLoader.load( jsonSchema ).validate( returnJSON );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return returnJSON;
    }
}
