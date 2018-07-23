package org.sbm.demo;

import org.sbm.demo.data.Company;
import org.sbm.demo.data.Person;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;

import com.mongodb.MongoClientSettings;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * simple example assuming mongo is running on localhost without authentication
 */
public class Main {
    public static void main(String [] args) throws Throwable {
        System.out.println("create documents");

        Company c = new Company();
        c.setName("mongodb");
        c.setAddr("nyc");

        Person p = new Person();
        p.setFname("shawn");
        p.setLname("mccarthy");
        p.setCompany(c);

        System.out.println(p);
        System.out.println(c);

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClients.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register("org.sbm.demo.data").build())
        );

        MongoClient client = MongoClients.create(
                MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build()
        );

        MongoDatabase db = client.getDatabase("pojoDemo");
        MongoCollection<Person> coll = db.getCollection("person", Person.class);

        SubscriberHelper.ObservableSubscriber iSuccess = new SubscriberHelper.ObservableSubscriber<Success>();
        coll.insertOne(p).subscribe(iSuccess);
        iSuccess.await();

        SubscriberHelper.PrintSubscriber<Person> fSuccess =
                new SubscriberHelper.PrintSubscriber<Person>("find finished");
        coll.find().subscribe(fSuccess);
        fSuccess.await();
    }
}
