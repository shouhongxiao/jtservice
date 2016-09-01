package jtservice;

import jtservice.register.RedisRegistryService;

/**
 *
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RedisRegistryService redisRegistryService =new RedisRegistryService();
        redisRegistryService.registerPack("jtservice.service");
    }
}
