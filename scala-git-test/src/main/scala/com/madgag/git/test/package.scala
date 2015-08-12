/*
 * Copyright 2015 Roberto Tyley
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
 */

package com.madgag.git

import org.eclipse.jgit.lib.Repository
import java.io.File
import java.io.File.separatorChar
import com.madgag.compress.CompressUtil._
import com.google.common.io.Files
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.net.URL

package object test {
  def unpackRepo(fileName: String): Repository = {
    val resolvedGitDir = unpackRepoAndGetGitDir(fileName)
    require(resolvedGitDir.exists)
    FileRepositoryBuilder.create(resolvedGitDir)
  }

  def unpackRepoAndGetGitDir(fileName: String) = {
    val resource: URL = getClass.getResource(fileName)
    assert(resource != null, s"Resource for $fileName is null.")
    val rawZipFileInputStream = resource.openStream()
    assert(rawZipFileInputStream != null, s"Stream for $fileName is null.")

    val repoParentFolder = new File(Files.createTempDir(), fileName.replace(separatorChar, '_') + "-unpacked")
    repoParentFolder.mkdir()

    unzip(rawZipFileInputStream, repoParentFolder)
    rawZipFileInputStream.close
    repoParentFolder
  }
}
