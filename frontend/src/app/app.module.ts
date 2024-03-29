import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CoreModule} from './core/core.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ProjectsModule} from './projects/projects.module';
import {Interceptor} from './interceptor';
import {UsersModule} from './users/users.module';
import {ToastrModule} from 'ngx-toastr';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DialogsModule} from './dialogs/dialogs.module';
import { MainPageComponent } from './main-page/main-page.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {NgxSpinnerModule} from 'ngx-spinner';
import { ActiveTasksComponent } from './main-page/active-tasks/active-tasks.component';
import { ActiveTasksListComponent } from './main-page/active-tasks/active-tasks-list/active-tasks-list.component';
import { ActiveTasksFromProjectListComponent } from './main-page/active-tasks/active-tasks-list/active-tasks-from-project-list/active-tasks-from-project-list.component';


@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    NotFoundComponent,
    ActiveTasksComponent,
    ActiveTasksListComponent,
    ActiveTasksFromProjectListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CoreModule,
    UsersModule,
    ProjectsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    NgbModule,
    DialogsModule,
    NgxSpinnerModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
