import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowSubredditsComponent } from './show-subreddits.component';

describe('ShowSubredditsComponent', () => {
  let component: ShowSubredditsComponent;
  let fixture: ComponentFixture<ShowSubredditsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowSubredditsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowSubredditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
