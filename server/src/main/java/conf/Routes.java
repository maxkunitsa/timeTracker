/**
 * Copyright (C) 2012 the original author or authors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import controllers.ApplicationController;
import controllers.ProjectController;
import controllers.TaskController;
import controllers.UserController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
        
        /* User management */
        router.POST().route("/users/register").with(UserController.class, "register");
        router.GET().route("/users/login").with(UserController.class, "login");

        /* Project management */
        router.GET().route("/projects").with(ProjectController.class, "list");
        router.GET().route("/projects/{id}").with(ProjectController.class, "getById");
        router.POST().route("/projects/create").with(ProjectController.class, "create");
        router.DELETE().route("/projects/{id}").with(ProjectController.class, "delete");

        /* Task management */
        router.GET().route("/projects/{projectId}/tasks").with(TaskController.class, "list");
        router.POST().route("/projects/{projectId}/tasks").with(TaskController.class, "create");
        router.DELETE().route("/tasks/{taskId}").with(TaskController.class, "delete");

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
