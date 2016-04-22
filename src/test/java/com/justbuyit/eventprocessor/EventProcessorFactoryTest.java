package com.justbuyit.eventprocessor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import com.justbuyit.eventprocessor.subscription.CancelSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.ChangeSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.CreateSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.NotifySubscriptionEventProcessor;
import com.justbuyit.eventprocessor.user.AssignUserEventProcessor;
import com.justbuyit.eventprocessor.user.UnassignUserEventProcessor;
import com.justbuyit.model.event.EventType;

/**
 * Unit tests for {@link EventProcessorFactory}.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class EventProcessorFactoryTest {

    //@Autowired
    EventProcessorFactory eventProcessorFactory = new EventProcessorFactory();
    
    @Test
    public void testCreateEventProcessor() {
        eventProcessorFactory.setApplicationContext(new ApplicationContext() {
            
            @Override
            public Resource getResource(String location) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public ClassLoader getClassLoader() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public void publishEvent(Object event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void publishEvent(ApplicationEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public BeanFactory getParentBeanFactory() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public boolean containsLocalBean(String name) {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Object getBean(String name, Object... args) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public <T> T getBean(Class<T> requiredType) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Object getBean(String name) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String[] getAliases(String name) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public boolean containsBean(String name) {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String[] getBeanNamesForType(Class<?> type) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String[] getBeanNamesForType(ResolvableType type) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String[] getBeanDefinitionNames() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int getBeanDefinitionCount() {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public boolean containsBeanDefinition(String beanName) {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public Environment getEnvironment() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public long getStartupDate() {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public ApplicationContext getParent() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getId() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getDisplayName() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getApplicationName() {
                // TODO Auto-generated method stub
                return null;
            }
        });
        
        Assert.assertEquals(CreateSubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_ORDER).getClass());
        Assert.assertEquals(ChangeSubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CHANGE).getClass());
        Assert.assertEquals(CancelSubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CANCEL).getClass());
        Assert.assertEquals(NotifySubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_NOTICE).getClass());
        Assert.assertEquals(AssignUserEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.USER_ASSIGNMENT).getClass());
        Assert.assertEquals(UnassignUserEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.USER_UNASSIGNMENT).getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateEventProcessorWithNullType() {
        eventProcessorFactory.createEventProcessor(null);
    }
    
}
