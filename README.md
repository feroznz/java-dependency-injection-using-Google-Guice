# java-dependency-injection-using-Google-Guice

Working with frameworks like spring in java we take the dependency injection for granted but what if we are not using any framework and have to manage the dependency injection ourselves!.

Google Guice to the rescue!
 
**Google Guice: https://github.com/google/guice**


DI using Guice in 2 steps:
1. Define binding
```public class OrderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Order.class).to(OrderImpl.class);
    }
}
```
2. Build object using injector
```
Injector injector = Guice.createInjector(new OrderModule());
OrderService orderService = injector.getInstance(OrderService.class);
```

