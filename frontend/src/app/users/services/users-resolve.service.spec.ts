import { TestBed } from '@angular/core/testing';

import { UsersResolveService } from './users-resolve.service';

describe('UsersResolveService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UsersResolveService = TestBed.get(UsersResolveService);
    expect(service).toBeTruthy();
  });
});
