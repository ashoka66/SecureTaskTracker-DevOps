package com.ak.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class TaskService {
	
	//logger to track app activities
	public static final Logger logger=LoggerFactory.getLogger(TaskService.class);
	
	
	
	//In memory dataStructure to storing tasks
	//Key = unique taks-ID , Value= Task Content(String)
	private  Map<Integer,String> taskMap=new ConcurrentHashMap<>();
	
	
	//Executor Service used to manage background threads efficiently
	private final ScheduledExecutorService executorService=Executors.newScheduledThreadPool(1);
	
	
	public TaskService() {
		//start a background thread to auto-clean old tasks every 200 seconds
		
		executorService.scheduleAtFixedRate(()->{
			logger.info("BackGround Thread Running : Cleaning up old Tasks");
			if(taskMap.size( ) >5) {
				
				taskMap.clear();
			    logger.warn("Old tasks cleared up by cleanup thread");
			}
		},0,200,TimeUnit.SECONDS);
		
		
//		//Simultaneous multiple threads  adding tasks at the same time
//		for (int i = 0; i <= 10; i++) {
//		    int threadNum = i;
//		    new Thread(() -> {
//                for (int j = 0; j <= 1000; j++) {
//                    addTask("Task-" + threadNum + "-" + j);
//                }
//            }, "worker-" + i).start();
//		}

		
		
	}//task service end
	
	
//	//Add Task Into HashMap
//	public void addTask(String task) {
//		int id=taskMap.size() +1;
//		taskMap.put(id,task);
//		logger.info("Task added with id {}",id);
//		
//	}
	
	private final AtomicInteger idGenerator=new AtomicInteger(0);
	public void addTask(String task) {
	    int id = idGenerator.incrementAndGet();

	    // Log which thread is currently trying to add
	    logger.info("🧵 Thread {} adding task: {}", Thread.currentThread().getName(), task);

////	     Simulate a delay to increase chance of collision
//	    try {
//	        Thread.sleep(10);
//	    } catch (InterruptedException e) {
//	        Thread.currentThread().interrupt();
//	    }

	    taskMap.put(id, task);
	    logger.info("✅ Thread {} finished adding task with ID: {}", Thread.currentThread().getName(), id);
	}

	
	
	//return all tasks
	public Map<Integer,String> getAllTasks(){
		return taskMap;
	}
	
	public void deleteTask(int id) {
		taskMap.remove(id);
		logger.info("Task with ID {} removed ",id);
	}
	
	

}
