import { TestBed } from '@angular/core/testing';

import { SubredditRestService } from './subreddit-rest.service';

describe('SubredditRestService', () => {
  let service: SubredditRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubredditRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
