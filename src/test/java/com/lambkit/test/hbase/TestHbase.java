/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lambkit.test.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.lambkit.component.hbase.Hbase;
import com.lambkit.component.hbase.HbasePlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestHbase {
	
	public static void main(String[] args) throws IOException {
		Connection connection = null;
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
		config.set("hbase.zookeeper.property.client", "2181");
		try {
			connection = ConnectionFactory.createConnection(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Put put = new Put(Bytes.toBytes("first"));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("lambkit"));
		List<Put> puts = new ArrayList<Put>();
		puts.add(put);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf("user"));
			table.put(puts);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(table!=null) table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		connection.close();
	}
	/*
	private static HbasePlugin p;

	@BeforeClass
	public static void beforeClass() {
		// p = new HbasePlugin("127.0.0.1:2181");
		p = new HbasePlugin("node67:2181,node68:2181,node69:2181");
		p.start();
	}

	@AfterClass
	public static void afterClass() {
		p.stop();
	}

	@Test
	public void t1() {
		Hbase.createTable("sunyutable", new String[] { "f1", "f2" });
	}

	@Test
	@Ignore
	public void t2() {
		Hbase.deleteTable("sunyutable");
	}

	@Test
	public void t3() {
		HTableDescriptor desc = Hbase.tableDescriptor("sunyutable");
		System.out.println(desc);
	}

	@Test
	public void t4() {
		Put put = new Put(Bytes.toBytes("row1"));
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("q1"), Bytes.toBytes("f1列族q1列的值111"));
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("q2"), Bytes.toBytes("f1列族q2列的值111"));
		Hbase.put("sunyutable", put);
	}

	@Test
	public void t5() {
		List<Result> results = Hbase.scan("sunyutable");
		for (Result result : results) {
			byte[] value = result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("q1"));
			System.out.println(Bytes.toString(value));
		}
	}

	@Test
	public void t7() {
		Delete del = new Delete(Bytes.toBytes("row1"));
		Hbase.delete("sunyutable", del);
	}

	@Test
	public void t8() {
		Get g = new Get(Bytes.toBytes("row1"));
		Result result = Hbase.get("sunyutable", g);
		byte[] value = result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("q1"));
		System.out.println(Bytes.toString(value));
	}

	@Test
	@Ignore
	public void t9() {
		Hbase.addCoprocessor("sunyutable", "org.apache.hadoop.hbase.coprocessor.AggregateImplementation");
	}

	@Test
	public void t10() {
		System.out.println(Hbase.rowCount("sunyutable", new Scan()));
	}
	*/
}
