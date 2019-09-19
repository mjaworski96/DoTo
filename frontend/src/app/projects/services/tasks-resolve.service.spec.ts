import { TestBed } from '@angular/core/testing';

import { TasksResolveService } from './tasks-resolve.service';

describe('TasksResolveService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TasksResolveService = TestBed.get(TasksResolveService);
    expect(service).toBeTruthy();
  });
});
