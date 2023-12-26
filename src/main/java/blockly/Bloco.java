package blockly;

import cronapi.*;
import cronapi.rest.security.CronappSecurity;
import java.util.concurrent.Callable;
import org.springframework.web.bind.annotation.*;


@RestController
@CronapiMetaData(type = "blockly")
@CronappSecurity
public class Bloco {

public static final int TIMEOUT = 300;

/**
 *
 * @author Natali Amaral
 * @since 13/12/2023, 17:05:02
 *
 */
@RequestMapping(path = "/api/cronapi/rest/Bloco:Executar", method = RequestMethod.GET, consumes = "*/*")
public static Var Executar() throws Exception {
 return new Callable<Var>() {

   private Var x = Var.VAR_NULL;
   private Var lista = Var.VAR_NULL;
   private Var item = Var.VAR_NULL;
   private Var i = Var.VAR_NULL;
   private Var i_start = Var.VAR_NULL;
   private Var i_end = Var.VAR_NULL;
   private Var i_inc = Var.VAR_NULL;

   public Var call() throws Exception {
    lista =
    cronapi.list.Operations.newList();
    item =
    cronapi.json.Operations.getJsonOrMapField(
    cronapi.json.Operations.toJson(
    cronapi.util.Operations.getURLFromOthers(
    Var.valueOf("GET"),
    Var.valueOf("application/json"),
    Var.valueOf("https://bling.com.br/Api/v2/pedidos/vendas/json/?apikey=fff534935e08d2d87799c2dac9e05107f45a7ec6b8e94963ba2341e11ec830fee4f11ed7"), Var.VAR_NULL, Var.VAR_NULL, Var.VAR_NULL,
    Var.valueOf(""),
    Var.valueOf("BODY"))),
    Var.valueOf("retorno"));
    i_start =
    Var.valueOf(1);
    i_end =
    Var.valueOf(100);
    i_inc =
    Var.valueOf(1);
    if (i_start.greaterThan(i_end)) {
        i_inc.multiply(-1);
    }
    for (i = Var.valueOf(i_start);
        i_inc.getObjectAsInt() >= 0 ? i.getObjectAsLong() <= i_end.getObjectAsLong() : i.getObjectAsLong()  >= i_end.getObjectAsLong();
    i.inc(i_inc))  {
        x =
        cronapi.json.Operations.getJsonOrMapField(item,
        Var.valueOf(
        Var.valueOf("pedidos[").getObjectAsString() +
        cronapi.math.Operations.subtract(i,
        Var.valueOf(1)).getObjectAsString() +
        Var.valueOf("].pedido.cliente").getObjectAsString()));
        System.out.println(x.getObjectAsString());
        cronapi.list.Operations.addLast(lista,x);
    } // end for
    return lista;
   }
 }.call();
}

/**
 *
 * @param x
 *
 * @author Natali Amaral
 * @since 13/12/2023, 17:05:02
 *
 */
public static void formatar(@ParamMetaData(description = "x", id = "230a62cb") Var x) throws Exception {
  new Callable<Var>() {

   public Var call() throws Exception {
    if (
    Var.valueOf(x.equals(
    Var.valueOf("CPF"))).getObjectAsBoolean()) {
        cronapi.util.Operations.callClientFunction(Var.valueOf("cronapi.screen.changeAttrValue"),
        Var.valueOf("input6533"),
        Var.valueOf("mask"),
        Var.valueOf("999.999.999-99;0"));
        cronapi.util.Operations.callClientFunction(Var.valueOf("cronapi.screen.recompileComponent"),
        Var.valueOf("input6533"));
    } else {
        cronapi.util.Operations.callClientFunction(Var.valueOf("cronapi.screen.changeAttrValue"),
        Var.valueOf("input6533"),
        Var.valueOf("mask"),
        Var.valueOf("99.999.999/9999-99;0"));
        cronapi.util.Operations.callClientFunction(Var.valueOf("cronapi.screen.recompileComponent"),
        Var.valueOf("input6533"));
    }
   return Var.VAR_NULL;
   }
 }.call();
}

}

