import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterEvent } from '@angular/router';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import { ActiveTaskList } from 'src/app/models/task';
import { SessionStorageService } from 'src/app/shared/services/session-storage.service';

@Component({
  selector: 'app-active-tasks',
  templateUrl: './active-tasks.component.html',
  styleUrls: ['./active-tasks.component.css']
})
export class ActiveTasksComponent implements OnInit {
  destroyed = new Subject<any>();
  tasks: ActiveTaskList;

  constructor(private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.tasks = this.route.snapshot.data.activeTasks;
    this.reloadPageIfRouted();
  }


  reloadPageIfRouted() {
    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd),
      takeUntil(this.destroyed))
      .subscribe(res => {
        this.tasks = this.route.snapshot.data.activeTasks; 
      });
  }
}
