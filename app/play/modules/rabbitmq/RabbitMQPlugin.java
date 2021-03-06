/** 
 * Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Felipe Oliveira (http://mashup.fm)
 * 
 */
package play.modules.rabbitmq;

import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;

import play.*;
import play.modules.rabbitmq.util.ExceptionUtil;
import play.modules.rabbitmq.util.MsgMapper;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class RabbitMQPlugin.
 */
public class RabbitMQPlugin extends Plugin {

    public RabbitMQPlugin(Application app) {
        this.app = app;
    }

    Application app;
    /** The Constant factory. */
	public static final ConnectionFactory factory = new ConnectionFactory();

	/** The mapper. */
	private static MsgMapper mapper = null;
	
	/** The stats service. */
//	private static StatsService statsService = new StatsService();
	
	/** The consumers running. */
	private static boolean consumersActive = true;

	/**
	 * On application start.
	 */
	@Override
    public void onStart() {
		// Connection Factory
		factory.setUsername(getUserName());
		factory.setPassword(getPassword());
		factory.setVirtualHost(getVhost());
	}
	
	/**
	 * Consumers running.
	 *
	 * @return true, if successful
	 */
	public static boolean areConsumersActive() {
		return consumersActive;
	}
	
	/**
	 * Consumers running.
	 *
	 * @param b the b
	 */
	public static void consumersActive(boolean b) {
		consumersActive = b;
	}
	
	/**
	 * Stats service.
	 *
	 * @return the stats service
	 */
//	public static StatsService statsService() {
//		return statsService;
//	}

	/**
	 * Mapper.
	 * 
	 * @return the msg mapper
	 */
	public static MsgMapper mapper() {
		if ( mapper != null ) {
			return mapper;
		}
		String s = Play.application().configuration().getString("rabbitmq.msgmapper");
		if ((s != null) && StringUtils.isNotBlank(s)) {
			try {
				mapper = MsgMapper.Type.valueOf(s).get();
			} catch (Throwable t) {
				Logger.error(ExceptionUtil.getStackTrace(t));
				mapper = MsgMapper.Type.json.get();
			}
		} else {
			mapper = MsgMapper.Type.json.get();
		}
		Logger.info("RabbitMQ Message Mapper: %s", mapper);
		if ( mapper == null ) {
			throw new RuntimeException( "RabbitMQ Message Mapper is null! Config Parameter 'rabbitmq.msgmapper': " + s );
		}
		return mapper;
	}

	/**
	 * Gets the task channel.
	 * 
	 * @return the task channel
	 */
	protected Channel createChannel() {
		Channel channel = null;

		int attempts = 0;
		while (true) {
			attempts++;
			Logger.info("Attempting to connect to queue: attempt " + attempts);
			try {
				Connection connection = this.getConnection();
				channel = connection.createChannel();
				break;

			} catch (IOException e) {
				Logger.error("Error creating RabbitMQ channel, retrying in 5 secs - Exception: %s", ExceptionUtil.getStackTrace(e));
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException ex) {
				}
			}
		}
		return channel;
	}

	/**
	 * Creates the channel.
	 * 
	 * @param queue
	 *            the queue
	 * @return the channel
	 * @throws Exception
	 *             the exception
	 */
	public Channel createChannel(String queue, String routingKey) throws Exception {
		// Counter that keeps track of number of retries
		int attempts = 0;

		// Get Plugin
		RabbitMQPlugin plugin = Play.application().plugin(RabbitMQPlugin.class);

		// Create Channel
		Channel channel = this.createChannel();

		// Basic Qos
		if (RabbitMQPlugin.isBasicQos()) {
			int prefetchCount = 1;
			channel.basicQos(prefetchCount);
		}

		// Start Daemon
		while (true) {
			// Add to the number of retries
			attempts++;

			// Log Debug
			Logger.debug("Retry " + attempts);

			// Get Next Delivery Message
			try {
				// http://www.rabbitmq.com/api-guide.html
				// channel.exchangeDeclare(exchangeName, "direct", true);
				// String queueName = channel.queueDeclare().getQueue();
				// channel.queueBind(queueName, exchangeName, routingKey);
				
				channel.exchangeDeclare(queue, plugin.getExchangeType(), true);
				channel.queueDeclare(queue, plugin.isDurable(), false, false, null);
				channel.queueBind(queue, queue, routingKey);

				// Log Debug
				Logger.info("RabbitMQ Task Channel Available: " + channel);

				// Return Channel
				return channel;

			} catch (Throwable t) {
				// Log Debug
				Logger.error("Error establishing a connection to RabbitMQ, will keep retrying - Exception: %s", ExceptionUtil.getStackTrace(t));

				// Sleep a little while before retrying
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException ex) {
				}
			}
		}
	}

/**
	 * Creates the channel for a subscriber to consume messages from an exchange
	 * (Exchange is created.  Subscribers queue and binding by routing key are created)
	 * 
	 * @param exchangeName
	 *            the exchange name
	 * @param queue
	 *            the queue
	 * @param routingKey
	 *            the routing key
	 * @return the channel
	 * @throws Exception
	 *             the exception
	 */
	public Channel createSubscribersChannel(String exchangeName, String queue, String routingKey) throws Exception {
		// Counter that keeps track of number of retries
		int attempts = 0;

		// Get Plugin
		RabbitMQPlugin plugin = Play.application().plugin(RabbitMQPlugin.class);

		// Create Channel
		Channel channel = this.createChannel();

		// Basic Qos
		if (RabbitMQPlugin.isBasicQos()) {
			int prefetchCount = 1;
			channel.basicQos(prefetchCount);
		}

		// Start Daemon
		while (true) {
			// Add to the number of retries
			attempts++;

			// Log Debug
			Logger.debug("Retry " + attempts);

			// Get Next Delivery Message
			try {
				// http://www.rabbitmq.com/api-guide.html
				channel.exchangeDeclare(exchangeName, plugin.getExchangeType(), true);
				channel.queueDeclare(queue, plugin.isDurable(), false, false, null);
				channel.queueBind(queue, exchangeName, routingKey);

				// Log Debug
				Logger.info("RabbitMQ Task Channel Available: " + channel);

				// Return Channel
				return channel;

			} catch (Throwable t) {
				// Log Debug
				Logger.error("Error establishing a connection to RabbitMQ, will keep retrying - Exception: %s", ExceptionUtil.getStackTrace(t));

				// Sleep a little while before retrying
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException ex) {
				}
			}
		}
	}

	/**
	 * Creates the channel to publish messages to an exchange
	 * (Exchange is created.  Queue and bindings are NOT created)
	 * 
	 * @param exchangeName
	 *            the exchange name
	 * @return the channel
	 * @throws Exception
	 *             the exception
	 */
	public Channel createPublishersChannel(String exchangeName) throws Exception {
		// Counter that keeps track of number of retries
		int attempts = 0;

		// Get Plugin
		RabbitMQPlugin plugin = Play.application().plugin(RabbitMQPlugin.class);

		// Create Channel
		Channel channel = this.createChannel();

		// Basic Qos
		if (RabbitMQPlugin.isBasicQos()) {
			int prefetchCount = 1;
			channel.basicQos(prefetchCount);
		}

		// Start Daemon
		while (true) {
			// Add to the number of retries
			attempts++;

			// Log Debug
			Logger.debug("Retry " + attempts);

			// Get Next Delivery Message
			try {
				// http://www.rabbitmq.com/api-guide.html
				channel.exchangeDeclare(exchangeName, plugin.getExchangeType(), true);

				// Log Debug
				Logger.info("RabbitMQ Task Channel Available: " + channel);

				// Return Channel
				return channel;

			} catch (Throwable t) {
				// Log Debug
				Logger.error("Error establishing a connection to RabbitMQ, will keep retrying - Exception: %s", ExceptionUtil.getStackTrace(t));

				// Sleep a little while before retrying
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException ex) {
				}
			}
		}
	}
		       
	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public static String getUserName() {
		String s = Play.application().configuration().getString("rabbitmq.username");
		if (s == null) {
			return "guest";
		}
		return s;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public static String getPassword() {
		String s = Play.application().configuration().getString("rabbitmq.password");
		if (s == null) {
			return "guest";
		}
		return s;
	}

	/**
	 * Checks if is auto ack.
	 * 
	 * @return true, if is auto ack
	 */
	public static boolean isAutoAck() {
		boolean autoAck = false;
		Boolean b = Play.application().configuration().getBoolean("rabbitmq.autoAck");
		if (b == null) {
			return autoAck;
		}
		return b;
	}

	/**
	 * Checks if is basic qos.
	 * 
	 * @return true, if is basic qos
	 */
	public static boolean isBasicQos() {
		boolean basicQos = true;
		Boolean b = Play.application().configuration().getBoolean("rabbitmq.basicQos");
		if (b == null) {
			return basicQos;
		}
		return b;
	}

	/**
	 * Retries.
	 * 
	 * @return the int
	 */
	public static int retries() {
		int defaultRetries = 5;
		try {
            Integer retries = Play.application().configuration().getInt("rabbitmq.retries");
			return retries;
		} catch (Throwable t) {
			Logger.error(ExceptionUtil.getStackTrace(t));
			return defaultRetries;
		}
	}

	/**
	 * Checks if is durable.
	 * 
	 * @return true, if is durable
	 */
	public static boolean isDurable() {
		boolean durable = true;
        Boolean b = Play.application().configuration().getBoolean("rabbitmq.durable");
		if (b == null) {
			return durable;
		}
		return b;
	}

	/**
	 * Gets the basic properties.
	 * 
	 * @return the basic properties
	 */
	public static BasicProperties getBasicProperties() {
		if (isDurable() == false) {
			return null;
		}
		BasicProperties b = MessageProperties.PERSISTENT_TEXT_PLAIN;
		return b;
	}

	/**
	 * Gets the exchange type.
	 * 
	 * @return the exchange type
	 */
	public static String getExchangeType() {
		String s = Play.application().configuration().getString("rabbitmq.exchangeType");
		if (s == null) {
			return "direct";
		}
		return s;
	}

	/**
	 * Gets the vhost.
	 * 
	 * @return the vhost
	 */
	public static String getVhost() {
        String s = Play.application().configuration().getString("rabbitmq.vhost");
		if (s == null) {
			return "/";
		}
		return s;
	}

        /**
         * Gets address using conf rabbitmq.seeds=host1[:port1];host2[:port2]...
         * 
         * @return 
         */
        public Address[] getAddress(String seeds) {
            List<Address> addresses = new ArrayList<>();
            if (seeds == null || seeds.isEmpty()) {
                addresses.add(new Address("localhost", 5672));
                return addresses.toArray(new Address[0]);
            }
            String[] stringArray = seeds.split("[;,\\s]");
            for (String s : stringArray) {
                String[] hostPort = s.split(":");
                if (0 == hostPort.length) {
                    continue;
                }
                String host = hostPort[0];
                int port = 5672;
                if (hostPort.length > 1) {
                    port = Integer.parseInt(hostPort[1]);
                }
                addresses.add(new Address(host, port));
            }
            return addresses.toArray(new Address[0]);
        }
        
	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
        public Connection getConnection() throws IOException {
            String seeds = Play.application().configuration().getString("rabbitmq.seeds");
            return factory.newConnection(getAddress(seeds));
        }
}
