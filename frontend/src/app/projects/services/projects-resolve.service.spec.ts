import { TestBed } from '@angular/core/testing';

import { ProjectsResolveService } from './projects-resolve.service';

describe('ProjectsResolveService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProjectsResolveService = TestBed.get(ProjectsResolveService);
    expect(service).toBeTruthy();
  });
});
