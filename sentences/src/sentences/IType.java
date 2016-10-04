package sentences;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("type")
public interface IType {
	
//	@GET
//	@Path("toString")
//	public String toString();
	
	@GET
	@Path("example")
	public String example();
	
	
}
