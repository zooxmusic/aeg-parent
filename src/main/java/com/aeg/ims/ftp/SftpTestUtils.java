/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aeg.ims.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.SessionCallback;
import org.springframework.integration.file.remote.session.Session;

import java.io.IOException;


/**
 * @author Gary Russell
 * @since 4.1
 *
 */
public class SftpTestUtils {


	public static boolean fileExists(RemoteFileTemplate<LsEntry> template, final String... fileNames) {
		if (template != null) {
			return template.execute(new SessionCallback<LsEntry, Boolean>() {

				@Override
				public Boolean doInSession(Session<LsEntry> session) throws IOException {
					// TODO: avoid DFAs with Spring 4.1 (INT-3412)
					ChannelSftp channel = (ChannelSftp) new DirectFieldAccessor(new DirectFieldAccessor(session)
							.getPropertyValue("targetSession")).getPropertyValue("channel");
					for (int i = 0; i < fileNames.length; i++) {
						try {
							SftpATTRS stat = channel.stat("Test/" + fileNames[i]);
							if (stat == null) {
								System.out.println("stat returned null for " + fileNames[i]);
								return false;
							}
						}
						catch (SftpException e) {
							System.out.println("Remote file not present: " + e.getMessage() + ": " + fileNames[i]);
							return false;
						}
					}
					return true;
				}
			});
		}
		else {
			return false;
		}
	}

}
