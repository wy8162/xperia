package y.w.concurrency.gpar

import groovyx.gpars.agent.Agent
class ShoppingCart {
    private def cartState = new Agent([:])
//----------------- public methods below here ----------------------------------
    public void addItem(String product, int quantity) {
        cartState << {it[product] = quantity; }  //the << operator sends
                                               //a message to the Agent
    }
    public void removeItem(String product) {
        cartState << {it.remove(product)}
    }    
    public Object listContent() {
        return cartState.val
    }    
    public void clearItems() {
        cartState << performClear
    }

    public void increaseQuantity(String product, int quantityChange) {
        cartState << this.&changeQuantity.curry(product, quantityChange)
    }
//----------------- private methods below here ---------------------------------
    private void changeQuantity(String product, int quantityChange, Map items) {
        items[product] = (items[product] ?: 0) + quantityChange
    }   
    private def performClear = { it.clear() }
}
//----------------- script code below here -------------------------------------
println "Thread Number: " + Thread.activeCount()
final ShoppingCart cart = new ShoppingCart()
cart.addItem 'Pilsner', 10
cart.addItem 'Budweisser', 5
cart.addItem 'Staropramen', 20
println "Thread Number: " + Thread.activeCount()
cart.removeItem 'Budweisser'
cart.addItem 'Budweisser', 15

println "Contents ${cart.listContent()}"

cart.increaseQuantity 'Budweisser', 3
println "Contents ${cart.listContent()}"
println "Thread Number: " + Thread.activeCount()
cart.clearItems()
println "Contents ${cart.listContent()}"
println "Thread Number: " + Thread.activeCount()