package blockly;

import cronapi.*;
import cronapi.rest.security.CronappSecurity;
import java.util.concurrent.Callable;


@CronapiMetaData(type = "blockly")
@CronappSecurity
public class Bloco {

public static final int TIMEOUT = 300;

/**
 *
 * @author Natali Amaral
 * @since 05/12/2023, 11:56:22
 *
 */
public static Var Executar() throws Exception {
 return new Callable<Var>() {

   private Var lista = Var.VAR_NULL;
   private Var item = Var.VAR_NULL;
   private Var i = Var.VAR_NULL;
   private Var x = Var.VAR_NULL;
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
        cronapi.list.Operations.addLast(lista,x);
    } // end for
    return lista;
   }
 }.call();
}

}

