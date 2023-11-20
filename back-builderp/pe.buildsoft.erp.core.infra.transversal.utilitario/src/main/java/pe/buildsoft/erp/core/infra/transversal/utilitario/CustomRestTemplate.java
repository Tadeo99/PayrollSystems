package pe.buildsoft.erp.core.infra.transversal.utilitario;

/*
import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
*/

public class CustomRestTemplate  /*extends RestTemplate*/ {

  /*  private MediaType defaultResponseContentType;

    public CustomRestTemplate() {
        super();
    }

    public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public void setDefaultResponseContentType(MediaType defaultResponseContentType) {
        this.defaultResponseContentType = defaultResponseContentType;
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor)
            throws RestClientException {

        return super.doExecute(url, method, requestCallback, new ResponseExtractor<T>() {
            public T extractData(ClientHttpResponse response) throws IOException {
                if (response.getHeaders().getContentType() == null && defaultResponseContentType != null) {
                    response.getHeaders().setContentType(defaultResponseContentType);
                }

                return responseExtractor.extractData(response);
            }
        });
    }*/
}
