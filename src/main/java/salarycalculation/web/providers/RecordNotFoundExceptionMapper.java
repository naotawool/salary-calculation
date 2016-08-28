package salarycalculation.web.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link RecordNotFoundException}のハンドリングクラス。
 *
 * @author naotake
 */
@Provider
public class RecordNotFoundExceptionMapper implements ExceptionMapper<RecordNotFoundException> {

    /**
     * 404 エラーの{@link Response}へ変換する。
     */
    @Override
    public Response toResponse(RecordNotFoundException exception) {
        return Response.serverError()
                       .status(404).build();
    }
}
